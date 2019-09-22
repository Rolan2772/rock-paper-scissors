import axios from 'axios';

const backendUrl = () => {
    return process.env.NODE_ENV === 'development' ? 'http://localhost:8080' : '';
};

const createGame = () => {
    return axios.get(`${backendUrl()}/game/create`)
        .then(resonse => resonse.data);
};

const loadGame = gameId => {
    return axios.get(`${backendUrl()}/game/${gameId}`)
        .then(resonse => resonse.data);
};

const makeMove = (gameId, playerId, move) => {
    console.log(`making move: ${move}`);
    return axios.post(`${backendUrl()}/game/${gameId}/${playerId}`, {move: move})
        .then(resonse => resonse.data);

};

export default {
    createGame,
    loadGame,
    makeMove
}