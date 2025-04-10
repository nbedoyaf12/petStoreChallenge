import http from 'k6/http';
import { check, sleep } from 'k6';
import { handleSummary } from '../../summary.js';


const orderIds = [1, 2, 3, 4, 5];  

export const options = {
    vus: 50,         
    duration: '10s', 
};

export default function () {
    orderIds.forEach((id) => {
        const res = http.get(`http://localhost:8080/api/v3/store/order/${id}`);

        check(res, {
            'is status 200': (r) => r.status === 200,
            'response time < 500ms': (r) => r.timings.duration < 500,
        });

        sleep(1);
    });
}
export { handleSummary };