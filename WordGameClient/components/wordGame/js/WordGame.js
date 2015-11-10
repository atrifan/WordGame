/**
 * Created by atrifan on 10/28/2015.
 */
define(['modal'], function (Modal) {
    function WordGame() {

    }

    WordGame.prototype.init = function () {
        var self = this;
        this._socket = this.context.getSocketToServer('WordsGame/wordGame');


    };

    WordGame.prototype.start = function () {
        this._root = this.context.getRoot();
        this._gameWrapper = this._root.find('.wordGameWrapper');
        this._usedWordsWrapper = this._root.find('.words-wrapper .card-body');
        this._wordShowLocation = this._root.find('.playable-area .letters');
        this._usersContainer = this._root.find('.users-container');
        this._chooseUserName();
        var self = this;

        this.context.getChildren().then(function (children) {
            var wordTyper = self._wordTyper = children['wordTyper'];
            wordTyper.on('keyPress', self._playWord.bind(self));
        });

        this._socket.on('registerSelf', function(data) {
            console.log('register', data);
            self._registered = true;
            if(data) {
                self._showGame();
                self._appendUsers(data.players);
                self._appendUsedWords(data.usedWords);
                self._showLetters(data.lettersToPlay, true)
            }
        });
        this._socket.on('joinedUser', function(user) {
            if(self._registered) {
                console.log('joined', user);
                self._appendUser(user);
            }
        });
        this._socket.on('leftUser', function(user) {
            console.log('left', user);
            self._removeUser(user);
        });

        this._socket.on('usedWord', this._appendUsedWord.bind(this));

        this._socket.on('lettersBroadcast', this._showLetters.bind(this));

        this._socket.on('updateScore', this._updateScore.bind(this));

    }

    WordGame.prototype._showLetters = function(data, dontReset) {
        if(!this._registered) {
            return;
        }

        this._wordShowLocation.html('');
        for(var i = 0; i < data.length; i++) {
            this.context.insertTemplate('letter', {
                letter: data[i]
            }, this._wordShowLocation);
        }

        if(!dontReset) {
            this._usersContainer.find('.scor').each(function (index, element) {
                $(element).text('0');
            });
            this._usedWordsWrapper.html('');
        }
    };

    WordGame.prototype._playWord = function(event) {
        if(event.keyCode == 13) {
            var word = this._wordTyper.value();
            if(word.trim().length > 0) {
                this._socket.emit('play', word.trim());
                this._wordTyper.value('');
            }
        }
    }
    WordGame.prototype._showGame = function () {
        this._gameWrapper.css('visibility', 'visible');
    }

    WordGame.prototype._appendUsedWord = function(word) {
        this.context.insertTemplate('word', {
            word: word
        }, this._usedWordsWrapper);
    };


    WordGame.prototype._chooseUserName = function () {

        var self = this;
        var inputText = $('<input id="userName" type="text" value="Enter userName"/>');
        this._loginModal = Modal.info('Choose a user name:', inputText[0].outerHTML, 'Submit');
        inputText = $('#userName');
        inputText.on('focus', function() {
            inputText.val('');
        });
        inputText.on('keypress', function(event) {
            var userName = inputText.val();
            if(event.keyCode == 13) {
                self._loginModal._destroy();
                self._registerUser(userName);
            }
        });
        this._loginModal.on('OK', function() {
            var userName = inputText.val();
            self._registerUser(userName);
        })
    };

    WordGame.prototype._appendUsers = function(users) {
        for(var i = 0; i < users.length; i++) {
            this._appendUser(users[i]);
        }
    };

    WordGame.prototype._appendUsedWords = function(usedWords) {
        for(var i = 0; i < usedWords.length; i++) {
            this._appendUsedWord(usedWords[i]);
        }
    }

    WordGame.prototype._appendUser = function (user) {
        var userContainer = this._usersContainer.find('#' + user.name);
        if(userContainer.length != 0) {
            return;
        }
        var locationToAppendUser = this._usersContainer.find('.card-body');
        console.log("APPENDING USER ", user);
        this.context.insertTemplate('user', user, locationToAppendUser);
    };

    WordGame.prototype._removeUser = function(user) {
        console.log(user);
        var userContainer = this._usersContainer.find('#' + user.name);
        userContainer.remove();
    }

    WordGame.prototype._registerUser = function(userName) {
        this._userName = userName;
        this._socket.emit('register', userName);
    };

    WordGame.prototype._updateScore = function(data) {
        console.log("GOT THE NEW SCOR ", data);
        var name = data.name,
            scor = data.scor;

        var scorLocation = this._usersContainer.find('#' + name).find('.scor');
        var currentScor = Number(scorLocation.text());
        if(scor != currentScor) {
            scorLocation.text(scor);
        }
    };

    return WordGame;
});
