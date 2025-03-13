import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import { handleSummary } from '../../summary.js';


export const options = {
    stages: [
        { duration: '10s', target: 20 }, 
        { duration: '30s', target: 50 }, 
        { duration: '10s', target: 0 },  
    ],
};

export default function () {
    const order = {
        id: randomIntBetween(1000, 9999),
        petId: randomIntBetween(1, 10),
        quantity: randomIntBetween(1, 5),
        shipDate: new Date().toISOString(),
        status: 'placed',
        complete: true
    };

    const headers = { 'Content-Type': 'application/json' };
    const res = http.post('http://localhost:8080/api/v3/store/order', JSON.stringify(order), { headers });

    check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
        'order id is valid': (r) => JSON.parse(r.body).id === order.id,
    });

    sleep(1);
}
export { handleSummary };