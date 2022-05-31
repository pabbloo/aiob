
import axios from 'axios';

const request = axios.create({
    baseURL: 'https://localhost:443',
    validateStatus: false,
    headers: {
        'Access-Control-Allow-Origin':'*',
        'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
        'Content-Type': 'application/json'
    }
});

export const authorizeUser = async ({username, password, mfaCode}) => {
    const response = await request.post('/login', {username, password, mfaCode});
    return response;
};

export const registerAccount = async ({username, password, email}) => {
    const response = await request.post('/signup', {username, password, email});
    return response;
}

export const getHrTable = async (token) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.get('/employees');
    return response;
}

export const deleteHrData = async ({id, token}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.delete(`/employees/${id}`);
    return response;
}

export const editHrData = async ({id, name, surname, personalNumber, salary, jobPosition, division, token}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.put(`/employees/${id}`, {id, name, surname, personalNumber, salary, jobPosition, division});
    return response;
}

export const getHrEntry = async ({id, token}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.get(`/employees/${id}`);
    return response;
};

export const addHrEntry = async ({name, surname, personalNumber, salary, jobPosition, division, token}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.post('/employees/', {name, surname, personalNumber, salary, jobPosition, division});
    return response;
}
