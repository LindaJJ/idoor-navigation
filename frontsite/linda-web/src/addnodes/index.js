import "./index.css"
import React ,{ Component } from 'react';
import { Form, Input, Button,Row, Col, Checkbox, Upload, Icon, Modal } from 'antd';
import $ from 'jquery';

class AddNodes extends Component{

    state = {
        previewVisible: false,
        previewImage: '',
        fileList: [],
        imgStr:'',
        nextNodeName:"",
        nextNodeDistance:"",
        nextNodeAngle:"",
        nodeName:"",
        unfinishedVisible:false
// {
//     uid: '-1',
//     name: 'xxx.png',
//     status: 'done',
//     url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
// }
    };

    handleCancel = () => this.setState({ previewVisible: false })

    handlePreview = (file) => {
        this.setState({
            previewImage: file.url || file.thumbUrl,
            previewVisible: true,
        });
    }

    handleChange = ({ fileList }) => {
        this.setState({ fileList });
        console.log(fileList);
        let imgStr;
        if(!fileList || !fileList[0]){
            imgStr = "";
        } else {
            imgStr = fileList[0].thumbUrl;
        }
        this.setState({imgStr})
    }

    handleSubmit = () => {
        let _this = this;
        if(!this.state.imgStr || !this.state.nodeName || !this.state.nextNodeName
            || !this.state.nextNodeAngle || !this.state.nextNodeDistance){
            this.setState({
                unfinishedVisible : true
            });
            return;
        }
        $.ajax({
            type:'post',
            url: "http://localhost:8080/node/newNode",
            data:_this.organizeData(),
            success(e, res, result) {
                console.log(result);
                window.location.href="";
            }
        })
    };

    end = () => {
        let _this = this;
        if(!this.state.imgStr || !this.state.nodeName){
            this.setState({
                unfinishedVisible : true
            });
            return;
        }
        $.ajax({
            type:'post',
            url: "http://localhost:8080/node/newNode",
            data:_this.organizeEndData(),
            success(e, res, result) {
                console.log(result);
                window.location.href="/chooseModel";
            }
        })
    };

    organizeData = () => {
        let data = {};
        data.nextNodeName = this.state.nextNodeName;
        data.nextNodeDistance = this.state.nextNodeDistance;
        data.nextNodeAngle = this.state.nextNodeAngle;
        data.imgStr = this.state.imgStr;
        data.nodeName = this.state.nodeName;
        return data;
    };

    organizeEndData = () => {
        let data = {};
        data.imgStr = this.state.imgStr;
        data.nodeName = this.state.nodeName;
        return data;
    }

    clear = () => {
        window.location.href = "/addNodes";
    }

    cancelUnfinishedModelVisible = () => {
        this.setState({
            unfinishedVisible : false
        })
    }


    render() {

        const { previewVisible, previewImage, fileList } = this.state;

        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
        };

        const uploadButton = (
            <div>
                <Icon type="plus" />
                <div className="ant-upload-text">Upload</div>
            </div>
        );

        return <div className="clearfix">
            <Form {...formItemLayout}>
                <Form.Item
                    label="当前节点"
                >
                    <Upload
                        listType="picture-card"
                        fileList={fileList}
                        onPreview={this.handlePreview}
                        onChange={this.handleChange}
                        action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                    >
                        {fileList.length >= 1 ? null : uploadButton}
                    </Upload>
                    <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
                        <img alt="example" style={{ width: '100%' }} src={previewImage} />
                    </Modal>
                </Form.Item>
                <Form.Item
                    label="当前节点名称"
                >
                    <Input onChange={(v)=>{this.setState({nodeName:v.target.value})}}/>
                </Form.Item>
                <Form.Item
                    label="下一节点名称"
                >
                        <Input onChange={(v)=>{this.setState({nextNodeName:v.target.value})}}/>
                </Form.Item>
                <Form.Item
                    label="下一节点距离"
                >
                    <Input onChange={(v)=>{this.setState({nextNodeDistance:v.target.value})}}/>
                </Form.Item>
                <Form.Item
                    label="下一节点角度"
                >
                    <Input onChange={(v)=>{this.setState({nextNodeAngle:v.target.value})}}/>
                </Form.Item>
            </Form>
            <Row type="flex" justify="space-around">
                <Col span={8}><Button id={"submitButton"} onClick={this.handleSubmit.bind(this)}>提交</Button></Col>
                <Col span={8}><Button id={"endButton"} onClick={this.end.bind(this)}>结束</Button></Col>
            </Row>
            <Modal
                title="仍有未填写信息"
                visible={this.state.unfinishedVisible}
                onOk={this.cancelUnfinishedModelVisible.bind(this)}
                onCancel={this.cancelUnfinishedModelVisible.bind(this)}
            >
                <p>请填写完所有信息</p>
            </Modal>
        </div>;
    }
}
export default AddNodes;
