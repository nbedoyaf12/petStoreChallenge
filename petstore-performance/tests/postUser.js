import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomString } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';
import { handleSummary } from '../summary.js';

export const options = {
  stages: [
    { duration: '10s', target: 10 },  
    { duration: '20s', target: 50 },  
    { duration: '20s', target: 100 }, 
    { duration: '10s', target: 0 }    
  ]
};

export default function () {
  const url = 'https://petstore.swagger.io/v2/user';

  const payload = JSON.stringify({
    id: Math.floor(Math.random() * 1000000),
    username: `user_${randomString(5)}`,
    firstName: 'Test',
    lastName: 'User',
    email: `test${randomString(5)}@example.com`,
    password: 'SecurePass123!',
    phone: '1234567890',
    userStatus: 1
  });

  const params = {
    headers: {
      'Content-Type': 'application/json'
    }
  };

  const res = http.post(url, payload, params);

  check(res, {
    'is status 200 or 201': (r) => r.status === 200 || r.status === 201,
    'response time < 500ms': (r) => r.timings.duration < 500
  });

  sleep(1);
}
export { handleSummary };