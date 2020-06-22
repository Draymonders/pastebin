import React from 'react';

import { 
  Layout,
  Typography
} from 'antd';
const { Title } = Typography;
const { Header } = Layout;

function PasteHeader() {
  return (
    <Header>
      <Title style={{"textAlign": "center", "color": "rgb(241, 241, 241)"}}>
        Pastebin
      </Title>
    </Header>
  )
}

export default PasteHeader;