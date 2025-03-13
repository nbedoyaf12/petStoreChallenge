import http from 'k6/http';
import { check, sleep } from 'k6';
import { getStatus } from '../../utils/data.js';
import { handleSummary } from '../../summary.js';


export const options = {
    vus: 50,
    duration: '30s',
};

export default function () {
    const res = http.get(`http://localhost:8080/api/v3/pet/findByStatus?status=${getStatus()}`);
    check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });
    sleep(1);
}
export { handleSummary };