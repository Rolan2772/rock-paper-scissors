[![BCH compliance](https://bettercodehub.com/edge/badge/Rolan2772/rock-paper-scissors?branch=master)](https://bettercodehub.com/)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=com.examples.games:rock-paper-scissors&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.examples.games:rock-paper-scissors)
[![Code Covarage](https://sonarcloud.io/api/project_badges/measure?project=com.examples.games:rock-paper-scissors&metric=coverage)](https://sonarcloud.io/dashboard?id=com.examples.games:rock-paper-scissors)
[![Build Status](https://travis-ci.org/Rolan2772/currency-converter.svg?branch=master)](https://travis-ci.org/Rolan2772/rock-paper-scissors)
[![Heroku](https://heroku-badge.herokuapp.com/?app=rolan-rock-paper-scissors)](https://rolan-rock-paper-scissors.herokuapp.com)

# rock-paper-scissors
[Rock Papepr Scissors](https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors) game implementation with player to player mode. Web application is available on any device of your choice.

### Game instructions
1. Open [game](https://rolan-rock-paper-scissors.herokuapp.com) in the browser
2. Click 'START NEW GAME' button
3. Copy 'Game reference' link and send it other player 
4. Make you move

### Tools
 - https://bettercodehub.com/ and https://sonarcloud.io/ - code anaysing tools
 - https://travis-ci.org - CI/CD tool
 - https://www.heroku.com/ - deployment environment
 
### System components
 - Backend - [Spring Boot](https://spring.io/projects/spring-boot) application using reactive [WebFlux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) endpoints
 - Frontend - [ReactJs](https://reactjs.org/) and [Material-UI](https://material-ui.com/)

### Build and run instructions
```
git clone https://github.com/Rolan2772/rock-paper-scissors.git
cd rock-paper-scissors
./gradlew bootRun
```
