import http from 'k6/http';
import { check, sleep } from 'k6';
import { handleSummary } from '../../summary.js';


export let options = {
    stages: [
        { duration: '10s', target: 50 },  
        { duration: '30s', target: 200 }, 
        { duration: '10s', target: 0 },   
    ]
};

export default function () {
    let username = 'testuser';  
    let res = http.get(`http://localhost:8080/api/v3/user/${username}`);
    
    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time is < 200ms': (r) => r.timings.duration < 200,
    });
    
    sleep(1); 
}

export { handleSummary };