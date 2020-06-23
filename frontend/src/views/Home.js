import React from 'react';
import { 
  Layout,
  Input,
  Form,
  Button,
  Select
} from 'antd';
import 'antd/dist/antd.css'; 
import PasteHeader from './Header'
import { apiCreatePaste } from '../api/index'

const { Content} = Layout;
const { TextArea } = Input; 
const { Option } = Select;


const layout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 16 },
};


class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      'poster': '',
      'language': '',
      'expire': '',
      'content': '#include<bits/stdc++.h><br/> using namespace std;'
    }
  }
  
  paste(val) {
    
    let paste = val.paste;
    paste.content = encodeURI(paste.content);
    console.log(paste)

    apiCreatePaste(paste)
      // .then(res => res.json())
      .then(res => res.data)
      .then(res => {
        console.log(res)
        if (res.code === 0) {
          let shortUrl = res.data.shortUrl;
          console.log("shortUrl", shortUrl);
          
          window.location.href = `/p/${shortUrl}`;      
        }
      })
  }

  render() {
    return (
      <Layout>
        <PasteHeader />

        <Content className="site-layout-background" style={{ margin: '24px 16px 0' }}>
          <Form {...layout} name="nest-messages" onFinish={this.paste}>
            {/* 提交人 */}
            <Form.Item name={['paste', 'poster']} label="Poster" rules={[{ required: true }]}>
              <Input />
            </Form.Item>

            {/* 语言 */}
            <Form.Item name={['paste', 'language']} label="Language" >
              <Select defaultValue="text" style={{ width: 120 }} >
                <Option value="text">text</Option>
                <Option value="cpp">cpp</Option>
                <Option value="java" >java</Option>
                <Option value="python" >python</Option>
                <Option value="javascript" >javascript</Option>
              </Select>
            </Form.Item>
            
            {/* 保存时间 */}
            <Form.Item name={['paste', 'expireTime']} label="Expiration" >
              <Select defaultValue="0" style={{ width: 120 }} >
                <Option value="0">None</Option>
                <Option value="86400000" >A Day</Option>
                <Option value="604800000" >A Week</Option>
                <Option value="2419200000" >A Month</Option>
              </Select>
            </Form.Item>

            {/* 粘贴内容 */}
            <Form.Item name={['paste', 'content']} label="Content" rules={[{ required: true }]}>
              <TextArea autoSize={true} />
            </Form.Item>
            <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
              <Button type="primary" htmlType="submit">
                Paste
              </Button>
            </Form.Item>
          </Form>
        </Content>
        
      </Layout>
    )
  }
}

export default Home;