import "./index.css"
import React ,{ Component } from 'react';
import { Form, Input, Button,Row,Tabs, Col, Checkbox, Upload, Icon, Modal } from 'antd';
import $ from 'jquery';

const TabPane = Tabs.TabPane;

class ChooseStart extends Component{

    state = {
        previewVisible: false,
        previewImage: '',
        fileList: [],
        startNodeName: "",
        endNodeName: "",
        similarFileList:[],
        confirmPic:false,
        currentFile:{},
        picChooseKey:"1"
    };

    handleCancel = () => this.setState({ previewVisible: false })

    handlePreview = (file) => {
        this.setState({
            previewImage: file.url || file.thumbUrl,
            previewVisible: true,
        });
    }

    handleChange = ({ fileList }) => {
        this.setState({ fileList :fileList})
        console.log(fileList)
    };

    handleCancelPicChoose = (e)=>{
        this.setState({
            confirmPic:false
        })
    };

    handlePicChooseOk = (e) => {
        let startNodeName = this.state.currentFile.name;
        this.setState({
            picChooseKey:"1",
            startNodeName:startNodeName,
            confirmPic:false
        })
    }

    handleSubmit = () => {
        window.location.href = "/showNodes/" + this.state.startNodeName + "/" + this.state.endNodeName;
    };

    organizeData = () => {
        let data = {};
        data.nextNodeName = this.state.nextNodeName;
        data.nextNodeDistance = this.state.nextNodeDistance;
        data.nextNodeAngle = this.state.nextNodeAngle;
    };

    getSimilarPics = () => {
        this.setState({
            similarFileList:[]
        })
        let _this = this;
        let fileList = _this.state.fileList;
        if(!fileList || fileList.length === 0){
            // todo something bad happen
            return;
        }
        let imgStr = fileList[0].thumbUrl;
        $.ajax({
            type: 'post',
            url: "http://localhost:8080/line/getSimilarPic",
            async: true,
            data: {"imgStr": imgStr},
            success(e, res, result) {
                let similarFileList = _this.constructSimilarFileList(JSON.parse(result.responseText));
                console.log(similarFileList);
                _this.setState({
                    similarFileList
                })
            }
        });
    };

    constructSimilarFileList(similarNodes){
        let similarFileList = [];
        for(let i = 0; i < similarNodes.length; i++){
            let similarNode = similarNodes[i];
            let similarFile = {};
            similarFile.uid = similarNode.nodeId;
            similarFile.name = similarNode.nodeName;
            similarFile.status = "done";
            similarFile.thumbUrl = similarNode.imgStr;
            similarFileList.push(similarFile);
        }
        return similarFileList;
    }

    render() {

        function onclick (){
            window.location.href="/showNodes.html?startName="+this.state.startNodeName+"&endName="+this.state.endNodeName;
        }

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

        function choosePic (file) {
            this.setState({
                confirmPic:true,
                currentFile:file
            });
            console.log(file)
        };

        function onChangePicChooseKey(v){
            this.setState({
                picChooseKey:v
            })
        }
        return <div className="clearfix">
            <Tabs defaultActiveKey="1" activeKey={this.state.picChooseKey} onChange={onChangePicChooseKey.bind(this)}>
                <TabPane tab="直接输入起始点名称" key="1">
                    <Form {...formItemLayout}>
                        <Form.Item
                            label="起点名称"
                        >
                            <Input value={this.state.startNodeName} onChange={(v)=>{this.setState({startNodeName:v.target.value})}}/>
                        </Form.Item>
                        <Form.Item
                            label="终点名称"
                        >
                            <Input onChange={(v)=>{this.setState({endNodeName:v.target.value})}}/>
                        </Form.Item>
                    </Form>
                    <Button onClick={onclick.bind(this)} id={"startButton"}>选择</Button>
                </TabPane>
                <TabPane tab="或输入起点照片" key="2">
                    <Form.Item
                        label="起点照片"
                    >
                        <Upload
                            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                            listType="picture-card"
                            fileList={fileList}
                            onPreview={this.handlePreview}
                            onChange={this.handleChange}
                        >
                            {fileList.length >= 1 ? null : uploadButton}
                        </Upload>
                        <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
                            <img alt="明细图" style={{ width: '100%' }} src={previewImage} />
                        </Modal>
                    </Form.Item>
                    <Button onClick={this.getSimilarPics.bind(this)} id={"startButton"}>图片对比</Button>
                    {this.state.similarFileList.length !== 0 &&
                        <Upload {...{
                            action: 'https://www.mocky.io/v2/5cc8019d300000980a055e76',
                            listType:"picture-card",
                            defaultFileList: this.state.similarFileList,
                            onPreview: choosePic.bind(this)
                        }}>
                            {null}
                        </Upload>
                    }
                    <Modal
                        title={"起点是否为" + this.state.currentFile.name}
                        visible={this.state.confirmPic}
                        onOk={this.handlePicChooseOk}
                        onCancel={this.handleCancelPicChoose}
                    >
                        <img alt="明细图" style={{ width: '100%' }} src={this.state.currentFile.thumbUrl} />
                    </Modal>
                </TabPane>
            </Tabs>
        </div>;
    }
}
export default ChooseStart;
