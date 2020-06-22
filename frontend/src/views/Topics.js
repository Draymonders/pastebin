import React from 'react';
import { useParams } from 'react-router';
import Topic from './Topic'


function Topics() {
  let { topicId } = useParams();

  return (
    <Topic topicId={topicId} content={"2333"}/>
  );
}


export default Topics;