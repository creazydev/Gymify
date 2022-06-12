import React from "react";
import {
    Button,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TablePagination,
    TableRow
} from "@mui/material";
import {SortableTableHead} from "../../shared/SortableTableHead";
import {gql, useLazyQuery, useMutation, useQuery} from "@apollo/client";
import DeleteIcon from '@mui/icons-material/Delete';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import EditIcon from '@mui/icons-material/Edit';
import IconButton from '@mui/material/IconButton';
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const WORKOUT_PLAN_PAGE_QUERY = gql`
    query WORKOUT_PLAN_PAGE_QUERY(
        $page: Int = 0,
        $pageSize: Int = 10,
        $direction: Direction! = ASC,
        $sort: String = "id"
    ) {
        currentUserWorkoutPlans(
            pageableRequest: {
                page: $page,
                pageSize: $pageSize,
                direction: $direction,
                sort: $sort
            },
            filter: {}
        ) {
            content {
                id
                creationTimestamp
                active
                name
                updateTimestamp
            }
            totalPages,
            totalElements
        }
    }
`;

const REMOVE_WORKOUT = gql`
    mutation REMOVE_WORKOUT($id: Int!) {
        deleteWorkoutPlan(id: $id)
    }
`

const headCells = [
    {
        id: 'id',
        numeric: false,
        disablePadding: true,
        label: 'ID',
    },
    {
        id: 'name',
        numeric: true,
        disablePadding: false,
        label: 'name',
    },
    {
        id: 'creationTimestamp',
        numeric: true,
        disablePadding: false,
        label: 'created_on',
    },
    {
        id: 'updateTimestamp',
        numeric: true,
        disablePadding: false,
        label: 'updated_on',
    },
    {
        id: 'actions',
        numeric: true,
        disablePadding: false,
        label: 'actions',
    },
];

export default function WorkoutPlanTable(query, options) {
    const { t, i18n } = useTranslation();

    const [order, setOrder] = React.useState('asc');
    const [orderBy, setOrderBy] = React.useState('id');
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const { loading, error, data, loadPage } = useQuery(
        WORKOUT_PLAN_PAGE_QUERY,
        {
            variables: {
                page: page,
                pageSize: rowsPerPage,
                direction: order.toUpperCase(),
                sort: orderBy
            },
            pollInterval: 500,
        }
    );

    const handleRequestSort = (event, property) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    return (
        <div>
            <TableContainer>
                <Table
                    sx={{minWidth: 750}}
                    aria-labelledby="tableTitle"
                    size={'medium'}
                >
                    <SortableTableHead
                        order={order}
                        orderBy={orderBy}
                        onRequestSort={handleRequestSort}
                        headCells={headCells}
                    />
                    <TableBody>
                        {data?.currentUserWorkoutPlans?.content
                            .map((row, index) => {
                                const labelId = `enhanced-table-checkbox-${index}`;
                                return (
                                    <TableRow
                                        hover
                                        onClick={(event) => console.log('clicked')}
                                        role="checkbox"
                                        className={row.active ? '' : ''}
                                        tabIndex={-1}
                                        key={row.id}
                                    >
                                        <TableCell
                                            component="th"
                                            id={labelId}
                                            scope="row"
                                            padding="none"
                                        >
                                            {row.id}
                                        </TableCell>
                                        <TableCell align="right">{row.name}</TableCell>
                                        <TableCell align="right">{row.creationTimestamp}</TableCell>
                                        <TableCell align="right">{row.updateTimestamp}</TableCell>
                                        <TableCell align="right">
                                            {!row.active &&
                                                <Button variant="contained" endIcon={<FitnessCenterIcon />} size="small">
                                                    {t('activate')}
                                                </Button>
                                            }
                                            <Link to={`/workout-plans/${row.id}`}>
                                                <IconButton color="secondary"
                                                            aria-label=""
                                                            component="span"
                                                            size="small">
                                                    <EditIcon />
                                                </IconButton>
                                            </Link>
                                            <Link to={`/workout-plans-delete/${row.id}`}>
                                                <IconButton color="error" aria-label="" component="span" size="small">
                                                    <DeleteIcon />
                                                </IconButton>
                                            </Link>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[5, 10, 25, 50]}
                component="div"
                count={data?.currentUserWorkoutPlans?.totalElements}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </div>
    );
}
