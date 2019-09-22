import React, {useEffect, useState} from 'react';
import {Link, withRouter} from 'react-router-dom'

import {Box, Grid, Typography} from '@material-ui/core';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import {makeStyles} from "@material-ui/core/styles/index";

import gameApiClient from '../../services/gameApiClient'

const useStyles = makeStyles(() => ({
    playerBoard: {
        margin: '10px',
        'min-height': '150px'
    },
    info: {
        margin: '5px'
    }
}));

export function Game(props) {

    const classes = useStyles();
    const [game, setGame] = useState({});
    const [move, setMove] = useState('');

    function loadParams(params) {
        return {
            gameId: params.gameId,
            playerId: parseInt(params.playerId, 10)
        }
    }

    const params = loadParams(props.match.params);

    useEffect(() => {
        async function fetchGame() {
            const game = await gameApiClient.loadGame(params.gameId);
            setGame(game);
            setMove(game.players[params.playerId - 1].move);
        }

        fetchGame();
    }, [props.match.params]);

    function makeMove(event, move) {
        if (move) {
            setMove(move);
            gameApiClient.makeMove(game.id, params.playerId, move)
                .then(game => {
                    console.log(`${JSON.stringify(game)}`);
                    setGame(game);
                });
        }
    }

    function gameLinks() {
        const opponentId = params.playerId === 1 ? 2 : 1;
        return (
            <Grid container justify="center">
                <Typography component="div">
                    <Box m={1}>
                        Game reference:
                        <Link to={`/game/${params.gameId}/${opponentId}`}>{`player ${opponentId}`}</Link>
                    </Box>
                </Typography>
            </Grid>
        )
    }

    function gameStatus() {
        const result = game.roundResult;
        let message = '';
        if (result) {
            if (result.draw) {
                message = 'draw';
            } else {
                message = 'you ' + (result.winner.id === params.playerId ? 'win' : 'lose');
            }
        }
        console.log(`result: ${JSON.stringify(result)}`);
        return (
            <Grid container justify="center" className={classes.playerBoard}>
                <Grid item xs>
                    <Typography align="center" className={classes.info}>
                        Game status
                    </Typography>
                </Grid>
                <Grid container justify="center">
                    <Typography align="center">
                        {message}
                    </Typography>
                </Grid>
            </Grid>
        )
    }

    function playerInfo(player) {
        return (
            <Typography align="center" className={classes.info}>
                Player {player.id}
            </Typography>
        )
    }

    function playerVariants() {
        return (
            <ToggleButtonGroup
                value={move}
                exclusive
                onChange={makeMove}
                aria-label="text alignment">
                <ToggleButton value="ROCK">
                    ROCK
                </ToggleButton>
                <ToggleButton value="PAPER">
                    PAPER
                </ToggleButton>
                <ToggleButton value="SCISSORS">
                    SCISSORS
                </ToggleButton>
            </ToggleButtonGroup>
        )
    }

    function playerBoard(player) {
        return (
            <Grid container justify="center" className={classes.playerBoard}>
                <Grid item xs>
                    {playerInfo(player)}
                </Grid>
                <Grid container justify="center">
                    {playerVariants()}
                </Grid>
            </Grid>
        )
    }

    function opponentMove(player) {
        const result = game.roundResult;
        let message = player.move ? 'already made a move' : 'waiting for move...';
        if (result) {
            message = `${player.move}`;
        }
        return (
            <Typography align="center">
                {message}
            </Typography>
        )
    }

    function opponentBoard(player) {
        return (
            <Grid container justify="center" className={classes.playerBoard}>
                <Grid item xs>
                    {playerInfo(player)}
                </Grid>
                <Grid container justify="center">
                    {opponentMove(player)}
                </Grid>
            </Grid>
        )
    }

    function board(playerId) {
        if (game.players) {
            const player = game.players[playerId - 1];
            return (player.id === params.playerId)
                ? playerBoard(player)
                : opponentBoard(player);
        }
        return null;
    }

    return (
        <React.Fragment>
            <Grid container spacing={2}>
                {gameLinks()}
            </Grid>
            <Grid container spacing={2}>
                <Grid item xs>
                    {board(1)}
                </Grid>
                <Grid item xs>
                    {gameStatus()}
                </Grid>
                <Grid item xs>
                    {board(2)}
                </Grid>
            </Grid>
        </React.Fragment>
    );
}

export default withRouter(Game);
