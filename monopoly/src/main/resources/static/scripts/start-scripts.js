function openModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
}
window.onclick = function(event) {
    if (event.target.className === 'modal') {
        event.target.style.display = 'none';
    }
}
async function hostGame() {
    const playerName = document.getElementById('hostName').value;
    if (playerName) {
        try {
            const baseUrl = window.location.origin;
            const response = await fetch(`${baseUrl}/api/game/host`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({ playerName })
            });
            if (response.ok) {
                window.location.href = `${baseUrl}/game/gameboard`;
            } else {
                alert("Failed to host game. Please try again.");
            }
        } catch (error) {
            console.error('Error:', error);
            alert("An error occurred. Please try again.");
        }
    }
}
async function joinGame() {
    const playerName = document.getElementById('joinName').value;
    const hostURL = document.getElementById('hostURL').value;
    if (playerName && hostURL) {
        try {
            const baseUrl = window.location.origin;
            const response = await fetch(`${baseUrl}/api/game/join`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({ playerName, hostURL })
            });
            if (response.ok) {
                window.location.href = `${baseUrl}/game/gameboard`;
            } else {
                alert("Failed to join game. Please try again.");
            }
        } catch (error) {
            console.error('Error:', error);
            alert("An error occurred. Please try again.");
        }
    }
}