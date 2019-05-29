import "./index.css"
import { Button } from 'antd';
import React, {Component} from 'react';

class Home extends Component{
    render() {
        return <div id={"backgroundDiv"}>
            <div id={"href"}></div>
            <Button onClick={()=>{window.location.href="/chooseModel"}} id={"startButton"}>开始</Button>
        </div>;
    }
}
export default Home;
