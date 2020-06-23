import React from 'react';
import SyntaxHighlighter from 'react-syntax-highlighter';
import { zenburn } from 'react-syntax-highlighter/dist/esm/styles/hljs';
import PasteHeader from './Header';
import { 
  Layout,
  Typography
} from 'antd';
import { apiGetPaste} from '../api/index';

const { Content} = Layout;
const { Title } = Typography;

class Topic extends React.Component {
  constructor(props) {
    super(props);
    let topicId = props.topicId;
    console.log(topicId)
    this.state = {
      content: "",
      poster: "",
      pasteDate: "",
      expireDate: "",
      language: ""
    }
    apiGetPaste(topicId)
      .then(res => res.data)
      .then(res => {
        console.log(res);
        if (res.code === 0) {
          let { content, poster, pasteDate, expireDate, language } = res.data;
          content = decodeURI(content);

          this.setState({
            content: content,
            poster: poster,
            pasteDate: pasteDate,
            expireDate: expireDate,
            language: language
          })
        }
      })
  }



  render() {
    return (
      <Layout>
        <PasteHeader />
        {
          this.state.language ? 
            <Content >     
              <div style={{"textAlign": "center"}}>
                <Title level={3}> {this.state.poster} 于{this.state.pasteDate} Paste</Title>
                {
                  this.state.expireDate ? 
                    <Title level={3}>将在 {this.state.expireDate} 到期</Title>
                  :
                    null
                }
              </div>
              <SyntaxHighlighter 
                language={this.state.language} 
                showLineNumbers={true} 
                style={zenburn}
                wrapLines={true}
              >      
                { this.state.content }
              </SyntaxHighlighter>
            </Content>
          :
            <Content>
              <div>你访问的page不存在</div>
            </Content> 
        }
      </Layout>
    );
  }
}

export default Topic;