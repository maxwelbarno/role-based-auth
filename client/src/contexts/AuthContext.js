import { createContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { useLocalStorage } from "../hooks/useLocalStorage";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();

  const [jwt, setJwt] = useLocalStorage("jwt");

  const [user, setUser] = useState();

  const userLogin = async (event) => {
    event.preventDefault();
    const res = await fetch("http://localhost:8080/api/v1/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: event.target.username.value,
        password: event.target.password.value,
      }),
    });

    const { data } = await res.json();
    if (res.status === 200) {
      setJwt(data.accessToken);

      // Set user using decoded JWT
      setUser(jwt_decode(data.accessToken));
      navigate("/");
    }
  };

  const userLogout = () => {
    setJwt(null);
    setUser(null);
    navigate("/login");
  };

  const contextData = { userLogin, user, userLogout };

  return (
    <AuthContext.Provider value={contextData}>{children}</AuthContext.Provider>
  );
};

export default AuthContext;
