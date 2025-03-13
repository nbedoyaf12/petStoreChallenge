import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';
import { handleSummary } from '../../summary.js';

export const options = {
  stages: [
    { duration: '5s', target: 50 },  
    { duration: '5s', target: 100 },  
    { duration: '5s', target: 200 }, 
    { duration: '5s', target: 0 }    
  ]
};

export default function () {
  const payload = JSON.stringify({
    id: Math.floor(Math.random() * 1000000),
    username: `name${randomString(5)}`,
    firstName: 'firstName',
    lastName: 'lastName',
    email: `email${randomString(2)}@mail.com`,
    password: '123',
    phone: `100200${randomString(2)}`,
    userStatus: 1
  });

  const params = {
    headers: {
      'Content-Type': 'application/json'
    }
  };

  const res = http.post('http://localhost:8080/api/v3/user', payload, params);

  check(res, {
    'is status 200 or 201': (r) => r.status === 200 || r.status === 201,
    'response time < 500ms': (r) => r.timings.duration < 500
  });

  sleep(1);
}
export { handleSummary };