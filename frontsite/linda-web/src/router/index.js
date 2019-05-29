
import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';
import App from './../App';

import ShowNodes from "../shownodes/index";
import AddNodes from "../addnodes";
import Home from "../home";
import ChooseModel from "../choosemodel";
import ChooseStart from "../chooseStart";


const Root = () => (
    <div>
        <Switch>
            <Route
                path="/"
                render={props => (
                    <App>
                        <Switch>
                            {/*<Route path="/" exact component={App} />*/}
                            <Route exact path="/showNodes/:startName/:endName" component={ShowNodes} />
                            <Route path="/addNodes" component={AddNodes} />
                            <Route path="/home" component={Home} />
                            <Route path="/chooseModel" component={ChooseModel} />
                            <Route path="/chooseStart" component={ChooseStart} />
                            {/*路由不正确时，默认跳回APP页面*/}
                            {/*<Route render={() => <Redirect to="/test" />} />*/}
                        </Switch>
                    </App>
                )}
            />
        </Switch>
    </div>
);

export default Root;
