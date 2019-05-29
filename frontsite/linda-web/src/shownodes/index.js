import React ,{ Component } from 'react';
class ShowNodes extends Component{
    constructor(props) {
        super(props);
        let url = "/showNodes.html?startName=" + this.props.match.params.startName + "&endName=" + this.props.match.params.endName;
        window.location.href = url;
    }

    componentWillMount(){

    }

    render() {
        let url = "/showNodes.html?startName=" + this.props.match.params.startName + "&endName=" + this.props.match.params.endName;
        // window.location.href = url;
        // return <div>
        //     <iframe src="/showNodes.html"></iframe>
        // </div>;
        // window.location.href = url;
        return <p>页面跳转中...</p>;
    }
}
export default ShowNodes;
