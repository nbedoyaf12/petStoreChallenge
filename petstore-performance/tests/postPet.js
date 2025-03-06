import http from 'k6/http';
import { check, sleep } from 'k6';
import { handleSummary } from '../summary.js';


export const options = {
    vus: 50,  
    duration: '30s',  
};

const validPet = {
    id: Math.floor(Math.random() * 1000000),
    category: { id: 1, name: "Dog" },
    name: "Rocky",
    photoUrls: ["https://example.com/dog.jpg"],
    tags: [{ id: 1, name: "friendly" }],
    status: "available"
};

const invalidPet = {
    id: "abc",  
    name: "",  
    status: "unknown_status" 
};

export default function () {
    const isValid = Math.random() > 0.5;
    const payload = JSON.stringify(isValid ? validPet : invalidPet);
    const params = { headers: { 'Content-Type': 'application/json' } };

    const res = http.post('https://petstore.swagger.io/v2/pet', payload, params);

    check(res, {
        'is status 200 (valid data)': (r) => isValid ? r.status === 200 : true,
        'is status 400 (invalid data)': (r) => !isValid ? r.status === 400 : true,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });

    sleep(1);
}
export { handleSummary };