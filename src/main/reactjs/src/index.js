import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route} from "react-router-dom";
import './index.css';
import App from './components/app/App';
import Game from './components/game/Game';

const routing = (
    <Router>
        <div>
            <Route exact path="/" component={App}/>
            <Route path="/game/:gameId/:playerId" component={Game}/>
        </div>
    </Router>
);

ReactDOM.render(routing, document.getElementById('root'));