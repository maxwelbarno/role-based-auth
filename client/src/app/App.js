import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "../styles/App.css";
import Login from "../pages/login/Login";
import { AuthProvider } from "../contexts/AuthContext";
import Home from "../pages/Home/Home";
import Header from "../components/Header";

const App = () => {
  return (
    <Router>
      <AuthProvider>
        <Header />
        <Routes>
          <Route element={<Home />} exact path="/" />
          <Route element={<Login />} path="/login" />
        </Routes>
      </AuthProvider>
    </Router>
  );
};

export default App;
