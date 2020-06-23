import React from 'react';
import {
  Layout,
  Typography
} from 'antd';
import {
  Chart,
  Interval,
  Tooltip,
  Axis,
  Coordinate
} from 'bizcharts';
import PasteHeader from './Header';
import {apiGetSummary} from '../api/index';
const { Title } = Typography;

class Summary extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: []
    }
    apiGetSummary()
      .then(res => res.data)
      .then(res => {
        console.log(res)
        this.setState({
          data: res
        })
      })
  }
  
  render() {
    const cols = {
      percent: {
        formatter: val => {
          val = val + '%';
          return val;
        },
      },
    };

    return (
      <Layout>
        <PasteHeader />
        <div style={{"textAlign": "center", "marginTop": "5px", "marginBottom": "5px"}}>
          <Title level={3}> 语言使用率统计 </Title>
        </div>
        <Chart  height={400} data={this.state.data} scale={cols} autoFit>
          <Coordinate type="theta" radius={0.75} />
          <Tooltip showTitle={true} />
          <Axis visible={false} />
          <Interval
            position="percent"
            adjust="stack"
            color="language"
            style={{
              lineWidth: 1,
              stroke: '#fff',
            }}
            label={['count', {
              content: (data) => {
                return `${data.language}: ${data.percent}%`;
              },
            }]}
          />
        </Chart>
      </Layout>
    );
  }
}

export default Summary;