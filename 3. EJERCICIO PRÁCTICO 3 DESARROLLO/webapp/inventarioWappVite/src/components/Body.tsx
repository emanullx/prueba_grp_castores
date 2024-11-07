import { Container } from "react-bootstrap";

const Body = ({ children }) => {

    return (
        <main>
            <Container style={ { maxWidth: "100%" } }>
                {children}
            </Container>
        </main>
    );
};


export default Body;