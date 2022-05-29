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
import {gql, useQuery} from "@apollo/client";
import DeleteIcon from '@mui/icons-material/Delete';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import EditIcon from '@mui/icons-material/Edit';
import IconButton from '@mui/material/IconButton';
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

const WORKOUT_SESSION_PAGE_QUERY = gql`
    query WORKOUT_SESSION_PAGE_QUERY(
        $page: Int = 0,
        $pageSize: Int = 10,
        $direction: Direction! = ASC,
        $sort: String = "id"
    ) {
        currentUserWorkoutSessions(
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
                updateTimestamp
            }
            totalPages,
            totalElements
        }
    }
`;

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

export default function WorkoutSessionTable() {
    const { t, i18n } = useTranslation();

    const [order, setOrder] = React.useState('asc');
    const [orderBy, setOrderBy] = React.useState('id');
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const { loading, error, data, loadPage } = useQuery(
        WORKOUT_SESSION_PAGE_QUERY,
        {
            variables: {
                page: page,
                pageSize: rowsPerPage,
                direction: order.toUpperCase(),
                sort: orderBy
            },
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
                        {data?.currentUserWorkoutSessions?.content
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
                                            <Link to={`/workout-sessions/${row.id}`}>
                                                <IconButton color="secondary"
                                                            aria-label=""
                                                            component="span"
                                                            size="small">
                                                    <EditIcon />
                                                </IconButton>
                                            </Link>
                                            <IconButton color="error" aria-label="" component="span" size="small">
                                                <DeleteIcon />
                                            </IconButton>
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
                count={data?.currentUserWorkoutSessions?.totalElements}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </div>
    );
}
