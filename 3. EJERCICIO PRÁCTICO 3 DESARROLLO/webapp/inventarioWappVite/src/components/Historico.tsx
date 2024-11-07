import { useEffect, useState } from 'react';
import { Table, Form } from 'react-bootstrap';
import { postData } from '../services/apiService';

const Historico = ({ user }) => {
  const [historicos, setHistoricos] = useState([]);
  const [filtro, setFiltro] = useState({
    maxRegPagina: 0,
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
      },
      usuario: {
        id: 0
      },
    }
  });
  const [tipoMovimientoFiltro, setTipoMovimientoFiltro] = useState('');

  const handleLoadHistorico = async () => {
    try {
      const response = await postData('/historicos/obtener', filtro);
      setHistoricos(response);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const loadFiltro = async () => {
    try {
      const response = await postData('/historicos/obtenerFiltro', filtro);
      setFiltro(response);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  useEffect(() => {
    loadFiltro();
  }, []);

  useEffect(() => {
    handleLoadHistorico();
  }, [filtro]);

  const handleTipoMovimientoChange = (e) => {
    setTipoMovimientoFiltro(e.target.value);
  };

  const filteredHistoricos = historicos.filter((historico) => {
    if (tipoMovimientoFiltro === '') return true;
    return historico.tipoMovimiento === parseInt(tipoMovimientoFiltro);
  });

  return (
    <div className="container mt-4">
      <h2 className="text-center">Historial de Productos</h2>

      <Form.Group controlId="tipoMovimientoFiltro" className="mb-3">
        <Form.Label>Filtrar por Tipo de Movimiento</Form.Label>
        <Form.Control as="select" value={tipoMovimientoFiltro} onChange={handleTipoMovimientoChange}>
          <option value="">Todos</option>
          <option value="1">Entrada</option>
          <option value="2">Salida</option>
        </Form.Control>
      </Form.Group>

      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>#</th>
            <th>Producto</th>
            <th>Tipo Movimiento</th>
            <th>Fecha</th>
            <th>Detalles</th>
          </tr>
        </thead>
        <tbody>
          {filteredHistoricos.map((historico) => (
            <tr key={historico.id}>
              <td>{historico.id}</td>
              <td>{historico.producto.nombre}</td>
              <td>{historico.tipoMovimiento === 1 ? 'Entrada' : historico.tipoMovimiento === 2 ? 'Salida' : 'Desconocido'}</td>
              <td>{new Date(historico.fechaMovimiento).toLocaleDateString()}</td>
              <td>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                  <li><strong>Ubicación:</strong> {historico.ubicacion}</li>
                  <li><strong>Stock Antes:</strong> {historico.stockAntes}</li>
                  <li><strong>Stock Después:</strong> {historico.stockDespues}</li>
                  <li><strong>Movimiento:</strong> {historico.movimiento}</li>
                  <li><strong>Usuario:</strong> {historico.usuario.nombre}</li>
                  <li><strong>Descripción:</strong> {historico.producto.descripcion}</li>
                  <li><strong>Precio:</strong> ${historico.producto.precio.toFixed(2)}</li>
                  <li>
                    <img src={historico.producto.urlImagen} alt={historico.producto.nombre} width="50" style={{ borderRadius: '5px' }} />
                  </li>
                </ul>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default Historico;