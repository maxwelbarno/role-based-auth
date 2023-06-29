import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./styles/App.css";
import Login from "./features/login/Login";
import Dashboard from "./features/dashboard/Dashboard";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
};

export default App;
