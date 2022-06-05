import React, { useState, useContext } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useParams, useNavigate} from 'react-router-dom';
import { sendSqlInjectionRequest, sendSqlInjectionRequestWithBasicAuthorization } from '../../../RequestHelper/RequestHelper';
import { ApplicationContext } from '../../../ApplicationContext/ApplicationProvider';

import './HrDataEditor.css';

const SqlInjectionForm = () => {
    const [ idForm, setIdForm ] = useState('');
    const [ returnedData, setReturnedData ] = useState([]);
    const [ ifBasic, setIfBasic ] = useState(false);
    const {token} = useContext(ApplicationContext);

    const editQuery = useMutation(ifBasic ? sendSqlInjectionRequestWithBasicAuthorization : sendSqlInjectionRequest);

    const handleOnSetIdChanged = (e) => setIdForm(e.target.value);
    const handleOnBasicChanged = () => setIfBasic(prevState => !prevState);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        editQuery.mutate({token, id: idForm}, {onSuccess: (response) => {
                if (response.status === 200)
                {
                    const { data } = response;
                    setReturnedData([...data]);
                } else {
                    alert(`Serwer wysłał odpowiedź ze statusem ${response.status}, spróbuj ponownie za chwile lub skontaktuj się z administratorem`);
                }
            }, onError: (error) => {
                alert(`Wystąpił błąd: ${error.message}, spróbuj wykonać operacje ponownie lub skontaktuj się z administratorem`);
            }});
    };

    const hrDataRow = returnedData.map(item => (
        <div key={item.id}>
            {item.name}, {item.surname}, {item.salary}
        </div>
    ));

    return (
        <div className="hr-data-container">
            <h1>Wybierz pracownika</h1>
            <div>
                <p>BASIC AUTHORIZATION</p>
                <input type="checkbox" checked={ifBasic} onChange={handleOnBasicChanged}/>
            </div>
            <form onSubmit={handleOnSubmit} className="display-grid hr-data-form">

                <div className="first-row f-column">
                    <label htmlFor="id" className="m-right-10">Id pracownika</label>
                </div>
                <div className="first-row s-column">
                    <input id="id" className="hr-data-input" type="text" value={idForm}
                           onChange={handleOnSetIdChanged} />
                </div>

                <div className="second-row s-column">
                    <button type="submit" className="button add-button hr-data-button">Dodaj</button>
                </div>
            </form>
            <h3>ODPOWIEDŹ SERWERA:</h3>
            {hrDataRow}
        </div>
    );
};

export default SqlInjectionForm;