import React, { useContext, useState } from 'react';
import { useQuery, useMutation } from 'react-query';

import './HrTable.css';

import { getHrTable, deleteHrData } from "../../../RequestHelper/RequestHelper";
import { ApplicationContext } from '../../../ApplicationContext/ApplicationProvider';
import { useNavigate } from 'react-router-dom';
import { ADD_HR_DATA, BASE_EDIT_HR_DATA } from '../../../common/WebsitePaths';

const HrTable = () => {
    const [ hrData, setHrData ] = useState([]);
    const { token } = useContext(ApplicationContext);
    const navigate = useNavigate();

    const deleteQuery = useMutation(deleteHrData);
    useQuery('getHrTable', () => getHrTable(token), { onSuccess: (response) => {
        if (response.status === 200) {
            const { data } = response;
            setHrData([...data]);
        }
    }});

    const deleteItem = (id) => {
        deleteQuery.mutate({id, token}, {onSuccess: (response) => {
            if (response.status === 200) {
                setHrData((prevState) => prevState.filter(item => item.id !== id));
            } else {
                alert(`Serwer wysłał odpowiedź ze statusem ${response.status}, spróbuj ponownie za chwile lub skontaktuj się z administratorem`);
            }
        }, onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}, spróbuj wykonać operacje ponownie lub skontaktuj się z administratorem`);
        }});
    }

    const editItem = (id) => {
        navigate(BASE_EDIT_HR_DATA + `/${id}`);
    }

    const addItem = () => {
        navigate(ADD_HR_DATA);
    }

    const hrDataRow = hrData.map(item => (
        <tr key={item.id}>
            <td>{item.name}</td>
            <td>{item.surname}</td>
            <td>{item.salary}</td>
            <td>
                <button className='button cancel-button' onClick={() => deleteItem(item.id)}>
                    Usuń
                </button>
                <button className='button edit-button' onClick={() => editItem(item.id)}>
                    Edytuj
                </button>
            </td>
        </tr>
    ));

    return (
        <div className='hr-container'>
            <h1>Dane pracowników</h1>
            <button className='button add-button' onClick={addItem}>Dodaj wpis</button>
            <table className='hr-table app-table'>
                <tbody>
                    <tr className="header-row">
                        <th>Imie</th>
                        <th>Nazwisko</th>
                        <th>Pensja</th>
                        <th>Działania</th>
                    </tr>
                    {hrDataRow}
                </tbody>
            </table>
        </div>
    );
};

export default HrTable;