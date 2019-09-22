import React from 'react';
import renderer from 'react-test-renderer';
import {BrowserRouter as Router} from 'react-router-dom';
import {Game} from './Game';

it('<Game/> player1 snapshot', () => {
    const match = {
        params: {
            gameId: '778d5697-e5a4-46c6-91da-7d656fd87a6c',
            playerId: '1'
        }
    };

    const tree = renderer
        .create(
            <Router>
                <Game match={match}/>
            </Router>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});

it('<Game/> player2 snapshot', () => {
    const match = {
        params: {
            gameId: '778d5697-e5a4-46c6-91da-7d656fd87a6c',
            playerId: '2'
        }
    };

    const tree = renderer
        .create(
            <Router>
                <Game match={match}/>
            </Router>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});
