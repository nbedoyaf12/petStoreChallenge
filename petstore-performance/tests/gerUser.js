import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '10s', target: 50 },  
        { duration: '30s', target: 200 }, 
        { duration: '10s', target: 0 },   
    ]
};

export default function () {
    let username = 'testuser';  
    let res = http.get(`https://petstore.swagger.io/v2/user/${username}`);
    
    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time is < 200ms': (r) => r.timings.duration < 200,
    });
    
    sleep(1); 
}
