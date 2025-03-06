export const petStatus = ['available', 'sold', 'pending'];
export function getStatus() {
    return petStatus[Math.floor(Math.random() * petStatus.length)];
}