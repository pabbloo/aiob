import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

import './App.css';

import NavBar from './components/NavBar/NavBar';
import Content from './components/Content/Content';
import ApplicationProvider from './ApplicationContext/ApplicationProvider';
import { QueryClient, QueryClientProvider } from "react-query";

const queryClient = new QueryClient();

const App = () => {
  return (
    <>
    <QueryClientProvider client={queryClient}>
      <ApplicationProvider>
          <Router>
              <NavBar/>
              <div className="content-wrapper">
                <Content />
              </div>
          </Router>
      </ApplicationProvider>
    </QueryClientProvider>
    </>
  );
}

export default App;
