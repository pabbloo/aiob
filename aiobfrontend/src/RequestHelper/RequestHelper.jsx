
import axios from 'axios';

let request = axios.create({
    baseURL: 'https://localhost:443',
    validateStatus: false,
    headers: {
        'Access-Control-Allow-Origin':'*',
        'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
        'Content-Type': 'application/json'
    }
});

export const changeRequestObjectToHttp = () => {
    request.defaults.baseURL = "http://localhost:8080";
}

export const changeRequestObjectToHttps = () => {
    request.defaults.baseURL = "https://localhost:443";
}

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

export const getHrTableWithBasicAuthentication = async (username, password) => {
    request.defaults.headers.common['Authorization'] = 'Basic ' + btoa(username + ":" + password);
    const response = await request.get('/employees');
    return response;
};

export const sendSqlInjectionRequest = async ({token, id}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.post(`/insecure`, {id});
    return response;
};

export const sendSqlInjectionRequestWithBasicAuthorization = async ({token, id}) => {
    request.defaults.headers.common['Authorization'] = 'Basic ' + btoa("user" + ":" + "user1");
    const response = await request.post(`/insecure`, {id});
    return response;
};

export const deleteHrData = async ({id, token}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.delete(`/employees/${id}`);
    return response;
}

export const editHrData = async ({id, name, surname, personalNumber, salary, jobPosition, division, isFired, token}) => {
    request.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    const response = await request.put(`/employees/${id}?isFired=${isFired}`, {id, name, surname, personalNumber, salary, jobPosition, division});
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
