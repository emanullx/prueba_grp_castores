

import {Navbar} from "./Navbar";

const Header = ({ user }) => {

    return (
        <header >
            <Navbar user={user} />           
        </header>
    );
};


export default Header;