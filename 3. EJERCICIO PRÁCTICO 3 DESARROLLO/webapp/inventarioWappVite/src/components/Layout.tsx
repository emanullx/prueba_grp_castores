import { useContext } from "react";
import Body from "./Body";
import Footer from "./Footer";
import Header from "./Head";

const Layout = ({ user, children }) => {

    return (
        <div>
            <Header user={user} />
            <Body >
                { children }
            </Body>
            <Footer />
        </div>
    )
}


export default Layout;