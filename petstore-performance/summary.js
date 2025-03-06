export function handleSummary(data) {
    console.log('Generating consolidated report...');
    return {
        'report.json': JSON.stringify(data, null, 2), 
    };
}