import React from 'react';
import {shallow} from 'enzyme';
import {Game} from './Game';

it('renders <Game/> without crashing', () => {
    const match = {
        params: {
            gameId: '778d5697-e5a4-46c6-91da-7d656fd87a6c',
            playerId: '1'
        }
    };

    shallow(<Game match={match}/>);
});