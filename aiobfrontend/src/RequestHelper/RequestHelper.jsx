
import axios from 'axios';

const request = axios.create({
    baseURL: 'https://localhost:44323',
    validateStatus: false,
    headers: {
        'Access-Control-Allow-Origin':'*',
        'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
        'Content-Type': 'application/json'
    }
});

export const authorizeUser = async (credentials) => {
    const response = await request.post('/api/user/authenticate', credentials);
    return response;
};

export const getHrTable = async () => {
    return {status: 200, data:[{
        id: 0,
        name: "Lukasz",
        surname: "Zietek",
        salary: 10000
    }, {
        id: 1,
        name: "Lukasz",
        surname: "Zietek",
        salary: 20000
    }]}
}

export const deleteHrData = async () => {
    return {status: 204};
}

export const editHrData = async ({name, surname, personalNumber, salary, jobPosition, division, ifFired}) => {
    return {status: 204};
}

export const getHrEntry = async (id) => {
    return { status: 200, data: {id: 0, name: 'Lukasz', surname: 'Zietek', personalNumber:'125125125',
     salary:20000, jobPosition: 'HR Specialist', division: 'HR', ifFired: false}};
};

export const addHrEntry = async ({name, surname, personalNumber, salary, jobPosition, division}) => {
    return {status: 201};
}
