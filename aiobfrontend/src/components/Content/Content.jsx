import React from 'react';
import {Routes, Route} from 'react-router-dom';

import './Content.css';
import { ADD_HR_DATA, EDIT_HR_DATA, HR_TABLE } from '../../common/WebsitePaths';
import HrTable from './subcomponents/HrTable';
import MainPage from './subcomponents/MainPage';
import HrDataEditor from './subcomponents/HrDataEditor';
import HrDataAdder from './subcomponents/HrDataAdder';

const Content = () => {
    return (
        <main className="content">
        <Routes>
            <Route path={HR_TABLE} exact element={<HrTable/>} />
            <Route path={EDIT_HR_DATA} exact element={<HrDataEditor/>} />
            <Route path={ADD_HR_DATA} exact element={<HrDataAdder/>} />
            <Route path="/" exact element={<MainPage/>} />
        </Routes>
        </main>
    );
};

export default Content;