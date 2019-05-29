import React ,{ Component } from 'react';
import {Tabs, Icon, Button} from 'antd';
import "./index.css"

const TabPane = Tabs.TabPane;

class ChooseModel extends Component{

    constructor(props, context){
        super(props, context);
        this.state={
            activityKey:2
        }

    }

    render() {

        function onclick (){
            if(this.state.activityKey == 1){
                window.location.href="/addNodes";
            }
            else{
                window.location.href="/chooseStart";
            }
        }

        function onchange(v) {
            this.setState({
                activityKey:v
            })
        }
        return <div>
        <Tabs defaultActiveKey="2" activityKey={this.state.activityKey} onChange={onchange.bind(this)}>
            <TabPane tab={<span><Icon type="edit" />创建路线</span>} key="1">
                <div id={"href1"}></div>
                在这个模式下你可以创建路线为他人导航。
            </TabPane>
            <TabPane tab={<span><Icon type="search" />搜索路线</span>} key="2">
                <div id={"href2"}></div>
                在这个模式下你可以搜索路线为自己导航。
            </TabPane>
        </Tabs>
            <Button onClick={onclick.bind(this)} id={"startButton"}>选择</Button>
        </div>
    }
}
export default ChooseModel;
