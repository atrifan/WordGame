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
        this._wordShowLocation = this._root.find('.playable-area .letters');
        this._usersContainer = this._root.find('.users-container');
        this._chooseUserName();
        var self = this;

        this._socket.on('registerSelf', function(currentUsers) {
            console.log('register', currentUsers);
            self._registered = true;
            if(currentUsers) {
                self._showGame();
                self._appendUsers(currentUsers);
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

        this._socket.on('lettersBroadcast', self._showLetters.bind(self));

    }

    WordGame.prototype._showLetters = function(data) {
        if(!this._registered) {
            return;
        }

        this._wordShowLocation.html('');
        for(var i = 0; i < data.length; i++) {
            this.context.insertTemplate('letter', {
                letter: data[i]
            }, this._wordShowLocation);
        }
    };

    WordGame.prototype._showGame = function () {
        this._gameWrapper.css('visibility', 'visible');
        this._socket.emit('getLetters');
    }


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

    WordGame.prototype._appendUser = function (user) {
        var userContainer = this._usersContainer.find('#' + user.name);
        if(userContainer.length != 0) {
            return;
        }
        var locationToAppendUser = this._usersContainer.find('.card-body');
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
    }

    return WordGame;
});
