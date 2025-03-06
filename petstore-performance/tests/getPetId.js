import http from 'k6/http';
import { check, sleep } from 'k6';

const petIds = [1, 2, 3, 4, 5];

export const options = {
    vus: 50,          
    duration: '10s',  
};

export default function () {
    petIds.forEach((id) => {
        const res = http.get(`https://petstore.swagger.io/v2/pet/${id}`);

        check(res, {
            'is status 200': (r) => r.status === 200,
            'response time < 500ms': (r) => r.timings.duration < 500,
        });

        sleep(1); 
    });
}