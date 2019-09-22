import React from 'react';
import {withRouter} from 'react-router-dom'

import {makeStyles} from '@material-ui/core/styles';
import {Button, Grid} from '@material-ui/core';

import gameApiClient from '../../services/gameApiClient'

const useStyles = makeStyles(() => ({
    button: {
        margin: '3px',
    }
}));

function App(props) {

    const classes = useStyles();

    function startGame() {
        gameApiClient.createGame()
            .then(gameId => props.history.push(`/game/${gameId}/1`));
    }

    return (
        <React.Fragment>
            <Grid container spacing={2} justify="center">
                <Grid item xs={12} container justify="center">
                    <Button variant="contained" onClick={startGame} className={classes.button}>
                        Start new game
                    </Button>
                </Grid>
            </Grid>
        </React.Fragment>
    );
}

export default withRouter(App);