import { generateSummaryReport } from 'k6-html-reporter';

const options = {
    jsonFile: 'report.json',
    output: 'report.html',
};

generateSummaryReport(options);