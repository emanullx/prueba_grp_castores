import { useEffect, useState } from 'react';
import ReactPaginate from 'react-paginate';
import { Card, Button, Row, Col, Modal, Form } from 'react-bootstrap';
import { postData, putData } from '../services/apiService';

const Inventario = ({user}) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [productList, setProductList] = useState([]);
  const [itemsPerPage, setItemsPerPage] = useState(5); 

  const [filtro, setFiltro] = useState({
    maxRegPagina: 5,
    total: 0,
    next: 0,
    offSet: 0,
    paginas: 0,
    datos: {
      id: 0,
      ubicacion: "",
      status: -1,
      producto: {
        id: 0
      }
    }
  });

  const hasPermission = (clavePermiso) => {

    if (user && user.rol && user.rol.permisos && user.rol.permisos.find((p) => p.permiso.clave === clavePermiso)) {
      return true;
    }

    return false;
  };

  const loadInventario = async () => {
    try {
      const updatedFiltro = {
        ...filtro,
        offSet: currentPage * itemsPerPage,
        maxRegPagina: itemsPerPage,
        next: itemsPerPage
      };
      const response = await postData('/inventarios/obtener', updatedFiltro);
      setProductList(response);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const loadFiltro = async () => {
    try {
      const updatedFiltro = {
        ...filtro,
        offSet: currentPage * itemsPerPage,
        maxRegPagina: itemsPerPage,
        next: itemsPerPage
      };
      const response = await postData('/inventarios/obtenerFiltro', updatedFiltro);
      setFiltro(response);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  useEffect(() => {
    loadFiltro();
  }, []);

  useEffect(() => {
    loadInventario();
  }, [currentPage, itemsPerPage]);

  const offset = currentPage * itemsPerPage;

  const handlePageClick = (event) => {
    setCurrentPage(event.selected);
  };

  const [showAddProductModal, setShowAddProductModal] = useState(false);
  const [newProduct, setNewProduct] = useState({
    ubicacion: '',
    producto: {
      nombre: '',
      descripcion: '',
      precio: 0,
      stock: 0,
      urlImagen: ''
    }
  });

  const handleAddProduct = async () => {
    try {

      await postData(`/productos/crear/${user.id}/${newProduct.ubicacion}`, newProduct.producto);
      setShowAddProductModal(false);
      setNewProduct({producto: {nombre: '', descripcion: '', precio: 0, stock: 0, urlImagen: ''}, ubicacion: ''});
      loadInventario();
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleUpdateInventario = async (inventario) => {
    try {

      const response = await putData(`/inventarios/actualizar/${user.id}/${inventario.producto.id}`, inventario);
      const updatedProductList = productList.map((item) =>
                        item.id === inventario.id ? response : item
                      );
      setProductList(updatedProductList);
    } catch (error) {
      console.error('Error:', error);
      return false;
    }
  };

  return (
    <div>
      <h2 className="my-4 text-center">Inventario de Productos</h2>
      <div className="d-flex justify-content-end mb-4">
        <Button variant="primary" disabled={!hasPermission("INV02")} onClick={() => setShowAddProductModal(hasPermission("INV02"))}>
          Agregar Producto
        </Button>
      </div>

      <Modal show={showAddProductModal} onHide={() => setShowAddProductModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Agregar Nuevo Producto</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formProductName">
              <Form.Label>Nombre del Producto</Form.Label>
              <Form.Control
                type="text"
                placeholder="Ingrese el nombre del producto"
                value={newProduct.producto.nombre}
                onChange={(e) => setNewProduct({ ...newProduct, producto: { ...newProduct.producto, nombre: e.target.value } })}
              />
            </Form.Group>
            <Form.Group controlId="formProductDescription">
              <Form.Label>Descripción</Form.Label>
              <Form.Control
                type="text"
                placeholder="Ingrese la descripción del producto"
                value={newProduct.producto.descripcion}
                onChange={(e) => setNewProduct({ ...newProduct, producto: { ...newProduct.producto, descripcion: e.target.value } })}
              />
            </Form.Group>
            <Form.Group controlId="formProductUbicacion">
              <Form.Label>Ubicación</Form.Label>
              <Form.Control
                type="text"
                placeholder="Ingrese la ubicación del producto"
                value={newProduct.ubicacion}
                onChange={(e) => setNewProduct({ ...newProduct, ubicacion: e.target.value })}
              />
            </Form.Group>
            <Form.Group controlId="formProductUrlImagen">
              <Form.Label>Url Imagen</Form.Label>
              <Form.Control
                type="text"
                placeholder="Ingrese la Url de Imagen del producto"
                value={newProduct.producto.urlImagen}
                onChange={(e) => setNewProduct({ ...newProduct, producto: { ...newProduct.producto, urlImagen: e.target.value } })}
              />
            </Form.Group>
            <Form.Group controlId="formProductPrice">
              <Form.Label>Precio</Form.Label>
              <Form.Control
                type="number"
                placeholder="Ingrese el precio del producto"
                value={newProduct.producto.precio}
                onChange={(e) => {
                  const newPrice = e.target.value < 0 ? 0 : e.target.value;
                  setNewProduct({ ...newProduct, producto: { ...newProduct.producto, precio: newPrice } });
                }}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowAddProductModal(false)}>
            Cancelar
          </Button>
          <Button variant="primary" onClick={handleAddProduct}>
            Agregar Producto
          </Button>
        </Modal.Footer>
      </Modal>

      <Row xs={1} md={2} lg={3} className="g-4">
        {productList.map((inventario) => (
          <Col key={inventario.id}>
            <Card style={{ height: '100%' }}>
              <Card.Img variant="top" src={inventario.producto.urlImagen} alt={inventario.producto.nombre} />
              <Card.Body>
                <Card.Title>{inventario.producto.nombre}</Card.Title>
                <Card.Text>{inventario.producto.descripcion}</Card.Text>
                <Card.Text>
                  <strong>Precio: ${inventario.producto.precio}</strong>
                </Card.Text>
                <Card.Text>
                  <strong>Ubicación: {inventario.ubicacion}</strong>
                </Card.Text>
                <Card.Text>
                  <strong>Estatus: {!inventario.status || inventario.status === 0 ? 'Inactivo' : 'Activo'}</strong>
                </Card.Text>
              </Card.Body>
              <Card.Footer>
                <div className="d-flex justify-content-between align-items-center">
                  <span>Cantidad existente:</span>
                  <input
                    disabled={!hasPermission("INV03")}
                    type="number"
                    value={inventario.stock}
                    onChange={(e) => {
                      const newStock = e.target.value < 0 ? 0 : e.target.value;
                      handleUpdateInventario({ ...inventario, stock: newStock });
                    }}
                    className="form-control"
                    style={{ width: '100px' }}
                  />
                </div>
              </Card.Footer>
            </Card>
          </Col>
        ))}
      </Row>

      <div className="d-flex justify-content-center mt-4">
        <ReactPaginate
          key="PaginadoInventario"
          previousLabel={'Anterior'}
          nextLabel={'Siguiente'}
          breakLabel={'...'}
          pageCount={Math.ceil(filtro.total / itemsPerPage)}
          marginPagesDisplayed={2}
          pageRangeDisplayed={5}
          onPageChange={handlePageClick}
          containerClassName={'pagination'}
          activeClassName={'active'}
          previousClassName={'page-item'}
          nextClassName={'page-item'}
          pageClassName={'page-item'}
          disabledClassName={'disabled'}
        />
      </div>
    </div>
  );
};

export default Inventario;