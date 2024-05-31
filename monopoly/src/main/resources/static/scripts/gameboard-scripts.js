document.addEventListener('DOMContentLoaded', () => {
        fetchGameSnapshot();

        setInterval(fetchGameSnapshot, 5000);
    });
    async function fetchGameSnapshot() {
        try {
            const baseUrl = window.location.origin;
            const response = await fetch(`${baseUrl}/api/game/snapshot`);
            if (response.ok) {
                const gameDTO = await response.json();
                //console.log('Fetched game snapshot:', gameDTO);
                if (gameDTO.status === 'NOT_STARTED') {
                    const modal = document.getElementById('notStartedModal');
                    const playerList = document.getElementById('playerList');
                    playerList.innerHTML = '';
                    gameDTO.players.forEach(player => {
                        const listItem = document.createElement('li');
                        listItem.textContent = player.playerName;
                        playerList.appendChild(listItem);
                    });
                    modal.style.display = 'block';
                } else if (gameDTO.status === 'FINISHED') {
                const modal = document.getElementById('finishedModal');
                const winnerNameElement = document.getElementById('winnerName');
                winnerNameElement.textContent = gameDTO.winnerPlayerName;
                modal.style.display = 'block';
                } else {
                    const modal = document.getElementById('notStartedModal');
                    modal.style.display = 'none';
                    updateBoard(gameDTO);
                    updatePlayerPositions(gameDTO);
                    updateCurrentPlayer(gameDTO);
                    updatePlayerInfo(gameDTO);
                }
            } else {
                console.error('Failed to fetch game snapshot.');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
    async function startTurn() {
    try {
        const baseUrl = window.location.origin;
        //console.log('Starting turn, baseUrl:', baseUrl);
        const response = await fetch(`${baseUrl}/api/game/startTurn`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        const result = await response.json();
        if (response.ok && !result.message) {
            //console.log('Turn started:', result);
            updateBoard(result);
            updatePlayerPositions(result);
            updateCurrentPlayer(result);
            updatePlayerInfo(result);
        } else {
            console.error('Failed to start turn:', result.message);
            document.getElementById('errorMessage').textContent = `Error: ${result.message}`;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('errorMessage').textContent = 'Error: An error occurred. Please try again.';
    }
}
    async function startGame() {
        try {
            const baseUrl = window.location.origin;
            const response = await fetch(`${baseUrl}/api/game/start`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            if (response.ok) {
                window.location.href = `${baseUrl}/game/gameboard`;
            } else {
                alert("Failed to start game. Please try again.");
            }
        } catch (error) {
            console.error('Error:', error);
            alert("An error occurred. Please try again.");
        }
    }
    async function buyProperty() {
    try {
        const baseUrl = window.location.origin;
        //console.log('Buying property, baseUrl:', baseUrl);
        const response = await fetch(`${baseUrl}/api/game/buyProperty`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        const result = await response.json();
        if (response.ok && !result.message) {
            //console.log('Property bought:', result);
            updateBoard(result);
            updatePlayerPositions(result);
            updateCurrentPlayer(result);
        } else {
            console.error('Failed to buy property:', result.message);
            document.getElementById('errorMessage').textContent = `Error: ${result.message}`;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('errorMessage').textContent = 'Error: An error occurred. Please try again.';
    }
}
    async function endTurn() {
    try {
        const baseUrl = window.location.origin;
        //console.log('Ending turn, baseUrl:', baseUrl);
        const response = await fetch(`${baseUrl}/api/game/endTurn`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
        const result = await response.json();
        if (response.ok && !result.message) {
            //console.log('Turn ended:', result);
            updateBoard(result);
            updatePlayerPositions(result);
            updateCurrentPlayer(result);
        } else {
            console.error('Failed to end turn:', result.message);
            document.getElementById('errorMessage').textContent = `Error: ${result.message}`;
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('errorMessage').textContent = 'Error: An error occurred. Please try again.';
    }
}
    async function buyHouse() {
    const fieldNumber = document.getElementById('fieldNumber').value;
        if (!fieldNumber) {
            document.getElementById('buyHouseErrorMessage').textContent = 'Error: Field number is required to buy a house.';
            return;
        }
        try {
            const baseUrl = window.location.origin;
            const response = await fetch(`${baseUrl}/api/game/buyHouse?fieldNumber=${fieldNumber}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            const result = await response.json();
            if (response.ok && !result.message) {
                console.log('House bought:', result);
                updateBoard(result);
                updatePlayerPositions(result);
                updateCurrentPlayer(result);
                closeBuyHouseModal();
            } else {
                console.error('Failed to buy house:', result.message);
                document.getElementById('buyHouseErrorMessage').textContent = `Error: ${result.message}`;
            }
        } catch (error) {
            console.error('Error:', error);
            document.getElementById('buyHouseErrorMessage').textContent = 'Error: An error occurred. Please try again.';
        }
}
    function updateBoard(gameDTO) {
        const playerColors = {};
        const colors = ['red', 'blue', 'darkgreen', 'purple'];

        gameDTO.players.forEach((player, index) => {
            playerColors[player.playerName] = colors[index % colors.length];
        });
        //console.log('Player colors mapping:', playerColors);
        gameDTO.properties.forEach(property => {
            const fieldNumber = property.fieldNumber;
            const owner = property.ownerPlayerName;
            const color = owner ? playerColors[owner] || 'gray' : 'gray';
            //console.log(`Tile ${fieldNumber}: owner=${owner}, color=${color}`);
            const tile = document.getElementById(`tile-${fieldNumber}`);
            if (tile) {
                //console.log(`Updating border color for tile-${fieldNumber}`);
                tile.style.borderColor = color;
            } else {
                //console.log(`Tile ${fieldNumber} not found`);
            }
        });
    }
    function updatePlayerPositions(gameDTO) {
        const board = document.getElementById('board');
        if (!board) {
            console.error('Board element not found');
            return;
        }

        const playerImages = ['player1.png', 'player2.png', 'player3.png', 'player4.png'];
        const playerOffsets = [-15, -5, 5, 15];
        const getGridPosition = (i) => {
            const rowStart = i < 8 ? 1 : (i < 15 ? i - 6 : (i < 22 ? 8 : 29 - i));
            const colStart = i < 8 ? i + 1 : (i < 15 ? 8 : (i < 22 ? 22 - i : 1));
            return { rowStart, colStart, rowEnd: rowStart + 1, colEnd: colStart + 1 };
        };

        const previousPlayers = document.querySelectorAll('.player-icon');
        previousPlayers.forEach(player => player.remove());
        gameDTO.players.forEach((player, index) => {
            const playerPosition = player.playerPosition;
            console.log(`Player ${index + 1} (${player.playerName}) position: ${playerPosition}`);

            const gridPosition = getGridPosition(playerPosition);
            //console.log(`Grid position for player ${index + 1}:`, gridPosition);

            const playerWrapper = document.createElement('div');
            playerWrapper.classList.add('player-icon');
            playerWrapper.style.gridArea = `${gridPosition.rowStart} / ${gridPosition.colStart} / ${gridPosition.rowEnd} / ${gridPosition.colEnd}`;
            playerWrapper.style.position = 'relative';

            const playerElement = document.createElement('div');
            playerElement.style.position = 'absolute';
            playerElement.style.top = '50%';
            playerElement.style.left = '50%';
            playerElement.style.transform = `translate(-50%, -50%) translateX(${playerOffsets[index]}px)`;
            playerElement.style.width = '60px';
            playerElement.style.height = '60px';
            playerElement.innerHTML = `<img src="/images/boardelem/${playerImages[index]}" alt="Player ${index + 1}" style="width: 100%; height: 100%;">`;

            playerWrapper.appendChild(playerElement);
            board.appendChild(playerWrapper);
            //console.log(`Player ${index + 1} icon added to the board`);
        });
    }
    function updateCurrentPlayer(gameDTO) {
        const currentPlayerElement = document.getElementById('currentPlayer');
        const errorMessageElement = document.getElementById('errorMessage');
        currentPlayerElement.textContent = `Current Player: ${gameDTO.currentPlayerName}`;
        errorMessageElement.textContent = '';
    }
    function updatePlayerInfo(gameDTO) {
        const playerInfoElement = document.getElementById('playerInfo');
        playerInfoElement.innerHTML = '';
        gameDTO.players.forEach(player => {
            const playerInfo = document.createElement('p');
            playerInfo.textContent = `${player.playerName} - Balance: $${player.playerBalance}`;
            if (player.jailTurns > 0) {
                playerInfo.textContent += ` - Jail Turns: ${player.jailTurns}`;
            }
            playerInfoElement.appendChild(playerInfo);
        });
    }
    function openBuyHouseModal() {
        document.getElementById('buyHouseModal').style.display = 'block';

    }
    function closeBuyHouseModal() {
        document.getElementById('buyHouseModal').style.display = 'none';
        document.getElementById('fieldNumber').value = '';
        document.getElementById('buyHouseErrorMessage').textContent = '';
    }