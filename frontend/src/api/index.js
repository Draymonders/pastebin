import fetch from './config'


export function apiCreatePaste(params) {
  return fetch({
    url: '/',
    method: 'post',
    params: params
  })
};

export function apiGetPaste(topicId) {
  return fetch({
    url: `/p/${topicId}`,
    method: 'get'
  })
};

export function apiGetSummary() {
  return fetch({
    url: "/summary"
  })
}