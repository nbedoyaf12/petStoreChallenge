import http from 'k6/http';
import { check, sleep } from 'k6';

const usernames = ['user1', 'user2', 'user3', 'user4', 'user5', 'nonexistentUser'];

export const options = {
    vus: 50,          
    duration: '10s',  
};

export default function () {
    usernames.forEach((username) => {
        const payload = JSON.stringify({
            id: Math.floor(Math.random() * 100000),
            username: username,
            firstName: "Test",
            lastName: "User",
            email: `${username}@test.com`,
            password: "password",
            phone: "1234567890",
            userStatus: 1
        });

        const headers = { 'Content-Type': 'application/json' };
        const res = http.post('https://petstore.swagger.io/v2/user', payload, { headers });

        check(res, {
            'is status 200 or 201': (r) => r.status === 200 || r.status === 201,
            'response time < 500ms': (r) => r.timings.duration < 500,
        });

        sleep(1);
    });

    usernames.forEach((username) => {
        const res = http.get(`https://petstore.swagger.io/v2/user/${username}`);

        check(res, {
            'is status 200 or 404': (r) => r.status === 200 || r.status === 404,
            'response time < 500ms': (r) => r.timings.duration < 500,
        });

        sleep(1);
    });

    usernames.forEach((username) => {
        const res = http.del(`https://petstore.swagger.io/v2/user/${username}`);

        check(res, {
            'is status 200 or 404': (r) => r.status === 200 || r.status === 404,
            'response time < 500ms': (r) => r.timings.duration < 500,
        });

        sleep(1);
    });
}
