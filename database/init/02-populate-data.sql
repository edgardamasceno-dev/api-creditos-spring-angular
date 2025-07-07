-- ====================================
-- SCRIPT DE POPULAÇÃO DE DADOS
-- API de Consulta de Créditos
-- ====================================

-- Inserir dados de exemplo conforme especificado no desafio
INSERT INTO credito (
    numero_credito, 
    numero_nfse, 
    data_constituicao, 
    valor_issqn,
    tipo_credito, 
    simples_nacional, 
    aliquota, 
    valor_faturado,
    valor_deducao, 
    base_calculo
) VALUES
    ('123456', '7891011', '2024-02-25', 1500.75, 'ISSQN', true, 5.0, 30000.00, 5000.00, 25000.00),
    ('789012', '7891011', '2024-02-26', 1200.50, 'ISSQN', false, 4.5, 25000.00, 4000.00, 21000.00),
    ('654321', '1122334', '2024-01-15', 800.50, 'Outros', true, 3.5, 20000.00, 3000.00, 17000.00),
    
    -- Dados adicionais para testes mais robustos
    ('456789', '5566778', '2024-03-10', 2250.00, 'ISSQN', true, 6.0, 37500.00, 2500.00, 35000.00),
    ('987654', '5566778', '2024-03-11', 1800.75, 'ISSQN', false, 5.5, 32500.00, 1500.00, 31000.00),
    ('147258', '9988776', '2024-01-20', 675.25, 'Outros', true, 4.0, 16750.00, 2000.00, 14750.00),
    ('369852', '3344556', '2024-02-05', 1125.00, 'ISSQN', true, 4.5, 25000.00, 3000.00, 22000.00),
    ('258147', '3344556', '2024-02-06', 960.00, 'ISSQN', false, 4.8, 20000.00, 2500.00, 17500.00),
    ('741963', '7766554', '2024-03-15', 1350.50, 'Outros', true, 5.0, 27000.00, 3500.00, 23500.00),
    ('159753', '7766554', '2024-03-16', 1080.00, 'ISSQN', false, 4.5, 24000.00, 2800.00, 21200.00),
    
    -- Dados para diferentes períodos
    ('111222', '1111111', '2023-12-10', 825.00, 'ISSQN', true, 3.0, 27500.00, 2750.00, 24750.00),
    ('333444', '2222222', '2023-11-25', 1575.75, 'Outros', false, 5.5, 28650.00, 3200.00, 25450.00),
    ('555666', '3333333', '2023-10-15', 945.50, 'ISSQN', true, 4.2, 22500.00, 1750.00, 20750.00),
    ('777888', '4444444', '2024-04-01', 1890.00, 'ISSQN', false, 6.3, 30000.00, 4500.00, 25500.00),
    ('999000', '5555555', '2024-04-02', 1265.25, 'Outros', true, 4.8, 26375.00, 3125.00, 23250.00);

-- Verificar se os dados foram inseridos corretamente
SELECT 
    COUNT(*) as total_registros,
    COUNT(DISTINCT numero_nfse) as total_nfse_distintas,
    COUNT(DISTINCT tipo_credito) as total_tipos_credito,
    MIN(data_constituicao) as data_mais_antiga,
    MAX(data_constituicao) as data_mais_recente
FROM credito;

-- Mostrar alguns registros para verificação
SELECT 
    numero_credito,
    numero_nfse,
    data_constituicao,
    valor_issqn,
    tipo_credito,
    CASE WHEN simples_nacional THEN 'Sim' ELSE 'Não' END as simples_nacional
FROM credito 
ORDER BY data_constituicao DESC 
LIMIT 5; 