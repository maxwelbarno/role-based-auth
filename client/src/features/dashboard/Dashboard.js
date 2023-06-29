import { getTokenFromLocalStorage } from "../../lib/common";
import "./Dashboard.css";
import axios from "axios";
const Dashboard = () => {
  const token = getTokenFromLocalStorage();

  const getUsers = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/v1/users", {
        method: "GET",
        withCredentials: true,
        // credentials: "include",
        headers: {
          //   Authorization: "Bearer " + token,
          //   "Content-Type": "text/plain",
        },
      });

      const data = await res.json();

      console.log(data);

      //   const res = await axios.get(
      //     "http://localhost:8080/api/v1/users",
      //     null,
      //     config
      //   );
    } catch (error) {
      console.log(error);
    }
  };
  getUsers();
  return (
    <div className="container">
      <h1>Welcome on Board!</h1>
    </div>
  );
};

export default Dashboard;
