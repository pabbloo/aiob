import React, {createContext, useState} from 'react';

export const ApplicationContext = createContext(null);

const ApplicationProvider = ({children}) => {
    const [token, setToken] = useState('');
    const [username, setUsername] = useState('');
    const [userId, setUserId] = useState('');
    const [role, setRole] = useState('');

    return (
        <ApplicationContext.Provider value={{token, setToken, username, setUsername, userId, setUserId, role, setRole}}>
            {children}
        </ApplicationContext.Provider>
    )
};

export default ApplicationProvider;